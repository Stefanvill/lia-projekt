package se.iths.stefan.liaprojekt.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.iths.stefan.liaprojekt.model.LiaSearch;
import se.iths.stefan.liaprojekt.model.User;
import se.iths.stefan.liaprojekt.repository.LiaSearchRepository;
import se.iths.stefan.liaprojekt.repository.UserRepository;

import java.util.List;

@Service
public class LiaSearchService {
    private final LiaSearchRepository searchRepository;
    private final UserRepository userRepository;

    public LiaSearchService(LiaSearchRepository searchRepository, UserRepository userRepository) {
        this.searchRepository = searchRepository;
        this.userRepository = userRepository;
    }

    private User getRequiredUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public List<LiaSearch> getPostsByUsername(String username) {
        User user = getRequiredUser(username);
        return searchRepository.findByUserUid(user.getUid());
    }

    public LiaSearch createSearch(LiaSearch liaSearch, String username) {
        User user = getRequiredUser(username);
        liaSearch.setUser(user);
        return searchRepository.save(liaSearch);
    }

    public List<LiaSearch> getPosts() {
        return searchRepository.findAll();
    }

    public LiaSearch updateSearch(Long id, LiaSearch updatedData, String username) {
        User user = getRequiredUser(username);
        LiaSearch existingPost = searchRepository.findByIdAndUserUid(id, user.getUid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id));

        existingPost.setCompanyName(updatedData.getCompanyName());
        existingPost.setPhoneNumber(updatedData.getPhoneNumber());
        existingPost.setEmail(updatedData.getEmail());
        existingPost.setWebsite(updatedData.getWebsite());
        existingPost.setContactPerson(updatedData.getContactPerson());
        existingPost.setAddress(updatedData.getAddress());
        existingPost.setCity(updatedData.getCity());
        existingPost.setStatus(updatedData.getStatus());
        existingPost.setNotes(updatedData.getNotes());
        existingPost.setApplicationSent(updatedData.getApplicationSent());
        existingPost.setInterviewDate(updatedData.getInterviewDate());

        return searchRepository.save(existingPost);
    }

    public LiaSearch getPost(Long id, String username) {
        User user = getRequiredUser(username);
        return searchRepository.findByIdAndUserUid(id, user.getUid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id));
    }

    public List<LiaSearch> getPostsByUser(Long userUid) {
        return searchRepository.findByUserUid(userUid);
    }

    public void deleteSearch(Long id, String username) {
        User user = getRequiredUser(username);
        LiaSearch post = searchRepository.findByIdAndUserUid(id, user.getUid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id));
        searchRepository.delete(post);
    }
}
