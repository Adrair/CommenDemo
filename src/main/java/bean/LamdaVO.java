package bean;

/**
 * @Description DOTO
 * @Author Sire
 * @Date 2021/6/9 14:19
 * @Version 1.0
 **/
public class LamdaVO {
    private Integer id;
    private String name;
    private String eamil;

    public LamdaVO() {
    }

    public LamdaVO(Integer id, String name, String eamil) {
        this.id = id;
        this.name = name;
        this.eamil = eamil;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEamil() {
        return eamil;
    }

    public void setEamil(String eamil) {
        this.eamil = eamil;
    }

    @Override
    public String toString() {
        return "LamdaVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", eamil='" + eamil + '\'' +
                '}';
    }
}
