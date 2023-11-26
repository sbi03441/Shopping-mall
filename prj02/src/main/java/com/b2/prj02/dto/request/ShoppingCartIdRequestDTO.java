package com.b2.prj02.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartIdRequestDTO {
    Set<Long> shoppingCartIdSet;
}
