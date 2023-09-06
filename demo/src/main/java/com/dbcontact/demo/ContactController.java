package com.dbcontact.demo;

import com.dbcontact.demo.entity.Contact;
import com.dbcontact.demo.entity.Email;
import com.dbcontact.demo.entity.Social;
import com.dbcontact.demo.entity.Tel;
import com.dbcontact.demo.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dbcontact.demo.database.ContactRepository;
import com.dbcontact.demo.database.UserRepository;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserRepository userRepository;

    // Criar Contato
    /*
     * @PostMapping("/create")
     * public ResponseEntity<Contact> createContactForUser(@RequestBody Contact
     * contactRequest) {
     * 
     * // Verificar se o contato tem um usuário associado (por exemplo, passando o
     * ID
     * // do usuário no corpo)
     * if (contactRequest.getUser() == null || contactRequest.getUser().getId() ==
     * null) {
     * return ResponseEntity.badRequest().build();
     * }
     * 
     * // Verificar se o usuário com o ID especificado existe
     * Optional<User> user =
     * userRepository.findById(contactRequest.getUser().getId());
     * if (!user.isPresent()) {
     * return ResponseEntity.notFound().build();
     * }
     * 
     * // Criar um novo contato e definir os atributos do contato com base nos dados
     * do
     * // ContactRequest
     * Contact newContact = new Contact();
     * newContact.setName(contactRequest.getName());
     * newContact.setLastName(contactRequest.getLastName());
     * newContact.setBusiness(contactRequest.getBusiness());
     * newContact.setNickname(contactRequest.getNickname());
     * newContact.setUser(user.get());
     * 
     * // Criar e associar objetos Tel e Social
     * List<Tel> tels = new ArrayList<>();
     * for (Tel telRequest : contactRequest.getTels()) {
     * Tel tel = new Tel();
     * tel.setRotulo(telRequest.getRotulo());
     * tel.setNumTel(telRequest.getNumTel());
     * tel.setContact(newContact);
     * tels.add(tel);
     * }
     * newContact.setTels(tels);
     * 
     * List<Social> socials = new ArrayList<>();
     * for (Social socialRequest : contactRequest.getSocials()) {
     * Social social = new Social();
     * social.setRotulo(socialRequest.getRotulo());
     * social.setNicknameSocial(socialRequest.getNicknameSocial());
     * social.setContact(newContact);
     * socials.add(social);
     * }
     * newContact.setSocials(socials);
     * 
     * // Salvar o novo contato no repositório
     * Contact savedContact = contactRepository.save(newContact);
     * 
     * // Retornar uma resposta com o novo contato criado e o status 201 (CREATED)
     * return ResponseEntity.status(201).body(savedContact);
     * }
     */

    @PostMapping("/create")
    public ResponseEntity<?> createContactForUser(@RequestBody Contact newContact) {

        // Verificar se o contato tem um usuário associado (por exemplo, passando o ID
        // do usuário no corpo)
        if (newContact.getUser() == null || newContact.getUser().getId() == null) {
            return ResponseEntity.badRequest().body("O ID do usuário é obrigatório.");
        }

        // Verificar se o usuário com o ID especificado existe
        Optional<User> user = userRepository.findById(newContact.getUser().getId());
        if (!user.isPresent()) {
            return ((BodyBuilder) ResponseEntity.notFound()).body("Usuário não encontrado com o ID especificado.");
        }

        // Associar o usuário ao contato
        newContact.setUser(user.get());

        // Salvar o novo contato no repositório
        Contact savedContact = contactRepository.save(newContact);

        // Retornar uma resposta com o novo contato criado e o status 201 (CREATED)
        return ResponseEntity.status(201).body(savedContact);
    }

    // Mostrar todos os contatos
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    // Buscar contato por ID
    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id " + id));
    }

    // Pesquisar contato por nome
    @GetMapping("/searchByName")
    public List<Contact> searchContactsByName(@RequestParam(name = "name") String name) {
        return contactRepository.findByNameContaining(name);
    }

    // Pesquisar contato por sobrenome
    @GetMapping("/searchByLastName")
    public List<Contact> searchContactsByLastName(@RequestParam(name = "lastName") String lastName) {
        return contactRepository.findByLastNameContaining(lastName);
    }

    // Pesquisar contato por nome e sobrenome
    @GetMapping("/searchByNameAndLastName")
    public List<Contact> searchContactsByNameAndLastName(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "lastName") String lastName) {
        return contactRepository.findByNameContainingAndLastNameContaining(name, lastName);
    }

    // Pesquisar contato por Empresa
    @GetMapping("/searchByBusiness")
    public List<Contact> searchContactsByBusiness(@RequestParam(name = "business") String business) {
        return contactRepository.findByBusinessContaining(business);
    }

    // Pesquisar contato por apelido.
    @GetMapping("/searchByNickname")
    public List<Contact> searchContactsByNickname(@RequestParam(name = "nickname") String nickname) {
        return contactRepository.findByNicknameContaining(nickname);
    }

}
