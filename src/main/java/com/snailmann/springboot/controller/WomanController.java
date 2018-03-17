package com.snailmann.springboot.controller;

import com.snailmann.springboot.dao.WomanRepository;
import com.snailmann.springboot.entity.Woman;
import com.snailmann.springboot.service.WomanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @GetMapping() is equal with @RequestMapping(method = RequestMethod.GET)
 * @PostMapper() is equal with @RequestMapping(method = RequestMethod.POST)
 * @RestController()  combines function of @Controller() and @ RequestBody()
 */
@RestController
@RequestMapping("/woman")
public class WomanController {

    @Autowired
    private WomanService womanService;

    @Autowired
    private WomanRepository womanRepository;

    /**
     * select all woman
     * @return
     */
    @GetMapping("/findwoman")
    public List<Woman> womanList() {

        return womanRepository.findAll();
    }

    /**
     * select a woman by id
     * @param id
     * @return
     */
    @GetMapping("/findwoman/id/{id}")
    public Woman findWomanById(@PathVariable("id") Integer id){
        Optional<Woman> woman=womanRepository.findById(id);
        return woman.get();
    }

    /**
     * select all woman by age
     * there use @PathVariable annotation,and it conforms to RESTful API
     * it can fetch value of id from url
     * @param age
     * @return
     */
    @GetMapping("/findwoman/age/{age}")
    public List<Woman> selectWomanByAge(@PathVariable("age") Integer age){
        return womanRepository.findByAge(age);
    }


    /**
     * insert a woman
     * @param woman   it will be transmit a Woman object
     * @param bindingResult  it is the result of validation
     * @return
     */
    @PostMapping("/insert")
    public Woman addWoman(@Valid Woman woman, BindingResult bindingResult){
        //if the verification is not passed , it will output the message that be defined in Woman Class
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return null;
        }
        woman.setAge(woman.getAge());
        woman.setCupSize(woman.getCupSize());

        return womanRepository.save(woman);
    }

    /**
     * insert two sql at the same time
     * test transaction management  @Transactional
     */
    @PostMapping("/insert-womans")
    public void addWomans(){
        womanService.insertTwo();
    }



    /**
     * update a woman infomation by id
     * @param id
     * @param cupSize
     * @param age
     * @return
     */
    @PutMapping("/update/id/{id}")
    public Woman updateWoman(@PathVariable("id") Integer id,String cupSize,Integer age){
        Woman woman=new Woman();
        woman.setId(id);
        woman.setCupSize(cupSize);
        woman.setAge(age);
        return womanRepository.save(woman);
    }

    /**
     * delete a woman by id
     * there use @PathVariable annotation,and it conforms to RESTful API
     * it can fetch value of id from url
     * @param id
     */
    @DeleteMapping("/delete/id/{id}")
    public void deletewoman(@PathVariable("id") Integer id){
        womanRepository.deleteById(id);
    }


}
