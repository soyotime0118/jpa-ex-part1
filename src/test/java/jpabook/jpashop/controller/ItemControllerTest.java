package jpabook.jpashop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpabook.jpashop.exception.NotExistItemException;
import jpabook.jpashop.service.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ItemService itemService;

    @DisplayName("상품수정시 상품 없을경우 500에러")
    @Test
    void NotFoundItemException() throws Exception
    {
        ItemModifyRequest modify = new ItemModifyRequest();
        modify.setName("new name");
        modify.setIsbn("new Isbm");
        modify.setPrice(100);
        modify.setAuthor("new author");
        modify.setStockQuantity(1000);

        doThrow(NotExistItemException.class).when(itemService).modify(anyLong(), any());

        mockMvc.perform(put("/items/100/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(modify)))
                .andExpect(status().isInternalServerError());
    }
}