package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("1° Method: seller findById");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("2° Method: seller findByDepartment");
        Department department = new Department(2, null);
        List <Seller> list = sellerDao.findByDepartment(department);
        for (Seller obj : list) {
            System.out.println(obj);
        }

        System.out.println("3° Method: seller findAll");
        list = sellerDao.findAll();
        for (Seller obj : list) {
            System.out.println(obj);
        }

        System.out.println("4° Method: seller insert");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());

        System.out.println("5° Method: seller update");
        Seller sellerToUpdate = sellerDao.findById(1);
        sellerToUpdate.setName("Martha Waine");
        sellerDao.update(sellerToUpdate);
        System.out.println("Update completed");

        System.out.println("6° Method: seller delete");
        System.out.println("Enter id for delete test: ");
        int id = scanner.nextInt();
        sellerDao.deleteById(id);
        System.out.println("Delete completed");

        System.out.println("7° Method: department findById");
        Department obj = departmentDao.findById(1);
        System.out.println(obj);

        System.out.println("8° Method: department findAll");
        List <Department> departmentList = departmentDao.findAll();
        for (Department departmentItem : departmentList) {
            System.out.println(departmentItem);
        }

        System.out.println("9° Method: department insert");
        Department newDepartment = new Department(null, "New department");
        departmentDao.insert(newDepartment);
        System.out.println("Inserted! New id = " + newDepartment.getId());

        System.out.println("10° Method: department update");
        Department departmentToUpdate = departmentDao.findById(1);
        departmentToUpdate.setName("Updated department");
        departmentDao.update(departmentToUpdate);
        System.out.println("Update completed");

        System.out.println("11° Method: department delete");
        System.out.println("Enter id for delete test: ");
        id = scanner.nextInt();
        departmentDao.deleteById(id);
        System.out.println("Delete completed");

        scanner.close();
    }
}
