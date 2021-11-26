package myf.config.result;

/**
 * User: mayongfu
 * Date: 2020/2/28
 * Time: 15:04
 * Description: No Description
 */

public enum NHttpstatusEnum {
    OK(200,"000000","操作成功"),
    LOGIN_ERROR(400,"222222","用户未登录"),

    ERROR(500,"111111","系统繁忙"),
    ;
    private final  int status;
    private final String code;
    private final String message;
    private NHttpstatusEnum(int status, String code, String message){
        this.code=code;
        this.status=status;
        this.message=message;
    }
    public static NHttpstatusEnum getEnumByCode(String code){
        NHttpstatusEnum[] nums=values();
        NHttpstatusEnum[] var2=nums;
        int var3 = nums.length;
        for (int var4 = 0;var4<var3;++var4){
            NHttpstatusEnum num =var2[var4];
            if(num.code.equals(code)){
                return num;
            }
        }
        return null;
    }



    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
