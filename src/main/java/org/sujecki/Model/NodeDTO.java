package org.sujecki.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NodeDTO {

    private Long nodeId;
    private String value;
    private Long parentId;
    @JsonProperty
    private Boolean isNewTreeRoot = false;

}
