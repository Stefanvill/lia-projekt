package se.iths.stefan.liaprojekt;

import org.springframework.stereotype.Service;
import se.iths.stefan.liaprojekt.model.LiaSearch;
import se.iths.stefan.liaprojekt.repository.LiaSearchRepository;

@Service
public class LiaSearchService {
    LiaSearchRepository searchRepository;

    public LiaSearch createSearch(LiaSearch liaSearch) {
        return searchRepository.save(liaSearch);
    }
}
