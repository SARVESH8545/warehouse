package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.model.OrderMethod;

public interface IOrderMethodService 
{
Integer saveOrderMethod(OrderMethod om);
void updateOrderMethod(OrderMethod om);

void deleteOrderMethod(Integer id);
Optional<OrderMethod> getOneOrderMethod(Integer id);

List<OrderMethod> getAllOrderMethods();
boolean isOrderMethodeExist(Integer id);

boolean isOrderMethodCodeExist(String code);
boolean isOrderMethodCodeExistEdit(String code, Integer id);

public List<Object[]> getOrderModeCount();
//pagination
Page<OrderMethod> getAllOrderMethods(Pageable pageable);

}
