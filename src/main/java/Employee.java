public class Employee {
    int id;
    String name ;
    int department ;
    int phone ;

    public Employee() {

    }

    public Integer getId(int id) {
        return this.id;
    }

    public int setId(Integer id) {
        this.id = id;
        return id;
    }

    public String getName(String name) {
        return this.name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public Integer getDepartment(int department) {
        return this.department;
    }

    public int setDepartment(Integer department) {
        this.department = department;
        return department;
    }



    public Integer getPhone(int phone) {
        return this.phone;
    }

    public int setPhone(Integer phone) {
        this.phone = phone;
        return phone;
    }

    public Employee(Integer id, String name, Integer department, Integer phone) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.phone = phone;
    }
    public void getEmployee(Integer id, String name, Integer department, String hire, Integer phone) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.phone = phone;
    }



    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department=" + department +
                ", phone=" + phone +
                '}';
    }
}
