package io.github.l4vid4.core.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Api {
    GET_BY_ID("getById"),
    LIST("list"),
    LIST_BY_CONDITION("listByCondition"),
    LIST_BY_IDS("listByIds"),
    PAGE("page"),
    PAGE_VO("pageVo"),
    SAVE("save"),
    UPDATE("update"),
    DELETE_BY_ID("deleteById"),
    DELETE("delete");

    private final String methodName;

    Api(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    // 反查：根据方法名找枚举
    public static Optional<Api> fromMethodName(String methodName) {
        return Arrays.stream(values())
                .filter(api -> api.methodName.equals(methodName))
                .findFirst();
    }
}
