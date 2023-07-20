package io.loopcamp.pojo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MinionSearch {

    //private List<Map<String, Object>> content;
    private List<Minion> content;
    private int totalElement;


}
