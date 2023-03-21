package edu.spring.btp.controllers

import edu.spring.btp.entities.Complaint
import edu.spring.btp.entities.Domain
import edu.spring.btp.entities.Provider
import edu.spring.btp.entities.User
import io.github.serpro69.kfaker.faker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/init")
class InitController {
    @Autowired
    lateinit var userRepository: edu.spring.btp.repositories.UserRepository

    @Autowired
    lateinit var domainRepository: edu.spring.btp.repositories.DomainRepository

    @Autowired
    lateinit var complaintRepository: edu.spring.btp.repositories.ComplaintRepository

    @Autowired
    lateinit var providerRepository: edu.spring.btp.repositories.ProviderRepository

    @Autowired
    lateinit var dbUserService: UserDetailsService

    private fun initProviders(count:Int){
        val faker = faker { }
        for (i in 1..count){
            val provider = Provider(faker.company.name())
            provider.address = faker.address.streetAddress()
            provider.postalCode = faker.address.postcode()
            provider.city = faker.address.city()
            provider.phone = faker.phoneNumber.phoneNumber()
            provider.domains.add(domainRepository.getRandomDomain())
            try {
                providerRepository.save(provider)
            }
            catch (e:Exception){
                println(e)
            }
        }
    }

    private fun initUsers(count:Int){
        val faker = faker { }
        for (i in 1..count){
            val user = User()
            user.username = faker.name.firstName()
            user.email = faker.internet.email()
            user.password = "password"
            user.role = "USER"
            try {
                userRepository.save(user)
            }
            catch (e:Exception){
                println(e)
            }
        }
    }

    private fun initDomains(count: Int){
        val faker = faker { }
        val domains = listOf("Education","Health","Transport","Food","Housing","Employment","Social Security","Environment","Other")
        val root= Domain()
        root.name="Root"
        domainRepository.save(root)
        domains.forEach{
            val domain = Domain()
            domain.name = it
            domain.parent = root
            domain.description = faker.lorem.words()
            domainRepository.save(domain)
        }
        val newDomains=domainRepository.findByParentName("Root");
        newDomains.forEach{
            val max= (1..count).random()
            for (i in 1..max){
                val domain = Domain()
                domain.name = faker.commerce.department()
                domain.parent = it
                domain.description = faker.lorem.supplemental()
                try {
                    domainRepository.save(domain)
                }catch (e:Exception){
                    println(e)
                }
            }
        }
    }

    private fun initComplaints(count:Int){
        val faker = faker { }
        for (i in 1..count){
            val complaint = Complaint()
            complaint.title = faker.lorem.words()
            complaint.description = faker.lorem.supplemental()
            val provider=providerRepository.getRandomProvider()
            complaint.provider = provider
            complaint.user = userRepository.getRandomUser()
            complaint.domain=domainRepository.getRandomDomainFromProvider(provider.id)
            val countUsers=userRepository.count()
            val max= (1..countUsers).random()
            for (i in 1..max){
                complaint.claimants.add(userRepository.getRandomUser())
            }
            try{
                complaintRepository.save(complaint)
            }
            catch (e:Exception){
                println(e)
            }
        }
    }

    @RequestMapping("/all/{count}")
    fun initFakeAll(@PathVariable count: Int): String{
        initDomains(count)
        initProviders(count*5)
        initUsers(count*5)
        initComplaints(count*5)
        return "redirect:/"
    }

    @RequestMapping("/provider/{count}")
    fun initFake(@PathVariable count: Int): String{
        initProviders(count)
        return "redirect:/"
    }
    @RequestMapping("/user/{count}")
    fun initFakeUser(@PathVariable count: Int): String{
        initUsers(count)
        return "redirect:/"
    }
    @RequestMapping("/domain/{count}")
    fun initFakeDomain(@PathVariable count: Int): String{
        initDomains(count)
        return "redirect:/"
    }

    @RequestMapping("/complaint/{count}")
    fun initFakeComplaint(@PathVariable count: Int): String{
        initComplaints(count)
        return "redirect:/"
    }
}