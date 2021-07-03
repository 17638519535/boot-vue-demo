package myf.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DeptInfo {

    private String deptName;
    private String theCompany;


    private List<User> users;

    private List<String> dates;

}
