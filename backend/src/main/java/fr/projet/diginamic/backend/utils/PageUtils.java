package fr.projet.diginamic.backend.utils;


import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.ArrayList;
import java.util.List;

public class PageUtils {

    /**Method to merge 2 page in one
     * @param page1, the first page to merge
     * @param page2, the second page to merge
     * @param size, the size of the page
     * @param page, the page of the new Page
     * @return the new Page of <T> .
     */
    public static <T> Page<T> mergePages(Page<T> page1, Page<T> page2, int size, int page) {
        // Combine the content of both pages into a single list
        List<T> combinedContent = new ArrayList<T>();
        combinedContent.addAll(page1.getContent());
        combinedContent.addAll(page2.getContent());

        // Determine the total number of elements and the pageable object
        int totalElements = (int) (page1.getTotalElements() + page2.getTotalElements());
        Pageable pagination = PageRequest.of(page, size);

        // Create a new PageImpl object with the combined content and the appropriate metadata
        return new PageImpl<T>(combinedContent, pagination, totalElements);
    }

}
