package com.khahnm04.ecommerce.shared.util;

import org.springframework.data.domain.Sort;

public class SortUtils {

    public static Sort parseSort(String sort) {
        if (sort == null || sort.trim().isEmpty()) {
            return Sort.unsorted();
        }

        String[] parts = sort.trim().split(",");
        String property = parts[0].trim();

        Sort.Direction direction = (parts.length > 1 && parts[1].equalsIgnoreCase("desc"))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return Sort.by(direction, property);
    }

}

//package com.khahnm04.ecommerce.util;
//
//import org.springframework.data.domain.Sort;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class SortUtils {
//
//    public static Sort parseSort(List<String> sorts) {
//        if (sorts == null || sorts.isEmpty()) {
//            return Sort.unsorted();
//        }
//
//        List<Sort.Order> orders = sorts.stream()
//                .filter(sort -> sort != null && !sort.trim().isEmpty())
//                .map(sort -> {
//                    String[] parts = sort.trim().split(",");
//                    String property = parts[0].trim();
//
//                    Sort.Direction direction = (parts.length > 1 && parts[1].equalsIgnoreCase("desc"))
//                            ? Sort.Direction.DESC
//                            : Sort.Direction.ASC;
//
//                    return new Sort.Order(direction, property);
//                })
//                .collect(Collectors.toList());
//
//        return orders.isEmpty() ? Sort.unsorted() : Sort.by(orders);
//    }
//
//}
