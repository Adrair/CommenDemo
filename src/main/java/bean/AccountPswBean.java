package bean;

public class AccountPswBean {

    private String acount_inf;//账户备注
    private String acount_nm;//登录账户
    private String acount_psw;//登录密码

    public String getAcount_inf_no() {
        return acount_inf_no;
    }

    public void setAcount_inf_no(String acount_inf_no) {
        this.acount_inf_no = acount_inf_no;
    }

    private String acount_inf_no;//账户号

    public String getAcount_inf() {
        return acount_inf;
    }

    public void setAcount_inf(String acount_inf) {
        this.acount_inf = acount_inf;
    }

    public String getAcount_nm() {
        return acount_nm;
    }

    public void setAcount_nm(String acount_nm) {
        this.acount_nm = acount_nm;
    }

    public String getAcount_psw() {
        return acount_psw;
    }

    public void setAcount_psw(String acount_psw) {
        this.acount_psw = acount_psw;
    }


    @Override
    public String toString() {
        return "AccountPswBean{" +
                "  acount_inf='" + acount_inf + '\'' +
                ", acount_nm='" + acount_nm + '\'' +
                ", acount_psw='" + acount_psw + '\'' +
                '}';
    }
}
