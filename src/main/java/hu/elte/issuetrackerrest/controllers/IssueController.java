package hu.elte.issuetrackerrest.controllers;

import hu.elte.issuetrackerrest.entities.Issue;
import hu.elte.issuetrackerrest.entities.Message;

import hu.elte.issuetrackerrest.repositories.IssueRepository;
import hu.elte.issuetrackerrest.repositories.MessageRepository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/issues")
public class IssueController {
    
    @Autowired
    private IssueRepository issueRepository;
    
    @Autowired
    private MessageRepository messageRepository;
    
    @GetMapping("")
    public ResponseEntity<Iterable<Issue>> getAll() {
        return ResponseEntity.ok(issueRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Issue> get(@PathVariable Integer id) {
        Optional<Issue> issue = issueRepository.findById(id);
        if (issue.isPresent()) {
            return ResponseEntity.ok(issue.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("")
    public ResponseEntity<Issue> post(@RequestBody Issue issue) {
        Issue savedIssue = issueRepository.save(issue);
        return ResponseEntity.ok(savedIssue);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Issue> delete(@PathVariable Integer id) {
        Optional<Issue> oIssue = issueRepository.findById(id);
        if (oIssue.isPresent()) {
            issueRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Issue> update(@PathVariable Integer id, @RequestBody Issue issue) {
        Optional<Issue> oIssue = issueRepository.findById(id);
        if (oIssue.isPresent()) {
            issue.setId(id);
            return ResponseEntity.ok(issueRepository.save(issue));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Iterable<Message>> message(@PathVariable Integer id) {
        Optional<Issue> oissue = issueRepository.findById(id);
        if (oissue.isPresent()) {
            return ResponseEntity.ok(oissue.get().getMessages());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{id}/messages")
    public ResponseEntity<Message> insertMessage(@PathVariable Integer id, @RequestBody Message message) {
        Optional<Issue> oissue = issueRepository.findById(id);
        if (oissue.isPresent()) {
            Issue issue = oissue.get();
            message.setIssue(issue);
            return ResponseEntity.ok(messageRepository.save(message));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
