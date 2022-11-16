package jpabook.jpashop.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ListApiResponse<T>
{
    private T data;
}
