package top.eviltuzki.tools.mybatissql.model.common;

import lombok.Data;

@Data
public class Result<T> {
    /**
     * 状态码
     */
    private Integer status;
    /**
     * 实际数据
     */
    private T data;
    /**
     * 对象为数组的时候Db结果数量
     */
    private Long count;
    /**
     * 状态码不为200时候，错误消息
     */
    private String msg;


    public static <T> Result<T> successResult(T data) {
        Result<T> result = new Result<>();
        result.setStatus(StatusCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static Result<String> successResult() {
        Result<String> result = new Result<>();
        result.setStatus(StatusCode.SUCCESS);
        result.setData("成功");
        return result;
    }

    public static <T> Result<T> successResult(T data, Long count) {
        Result<T> result = new Result<>();
        result.setStatus(StatusCode.SUCCESS);
        result.setData(data);
        result.setCount(count);
        return result;
    }


    public static <T> Result<T> unexpectedResult(Exception e) {
        Result<T> result = new Result<>();
        result.setStatus(StatusCode.SERVER_ERROR);
        result.setMsg(e.getMessage());
        return result;
    }
}
