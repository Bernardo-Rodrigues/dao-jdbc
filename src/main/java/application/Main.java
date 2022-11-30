package application;

import model.dao.DaoFactory;
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

        scanner.close();
    }
}
