package com.goit.javadev;

import com.goit.javadev.services.crud_service.ClientService;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws Exception {
        DatabaseQueryService databaseQueryService = new DatabaseQueryService();

        System.out.println("databaseQueryService.findLongestProject() = " + databaseQueryService.findLongestProject());
        System.out.println("databaseQueryService.findMaxProjectsClient() = " + databaseQueryService.findMaxProjectsClient());
        System.out.println("databaseQueryService.findMaxSalaryWorker() = " + databaseQueryService.findMaxSalaryWorker());
        System.out.println("databaseQueryService.findYoungestAndEldestWorkers() = " + databaseQueryService.findYoungestAndEldestWorkers());
        System.out.println("databaseQueryService.getProjectPrices() = " + databaseQueryService.getProjectPrices());

        try (Connection connection = Database.getInstance().getConnection()) {
            ClientService clientService = new ClientService(connection);

            System.out.println("clientService.create(\"John Week\") = " + clientService.create("John Week"));
            System.out.println("clientService.getById(5L) = " + clientService.getById(5L));

            System.out.println("clientService.getById(4L) = " + clientService.getById(4L));
            clientService.setName(4L, "Bart Bun");
            System.out.println("clientService.getById(4L) = " + clientService.getById(4L));

            System.out.println("clientService.getById(7L) = " + clientService.getById(7L));
            clientService.deleteById(7L);
            System.out.println("clientService.getById(7L) = " + clientService.getById(7L));

            System.out.println("clientService.listAll() = " + clientService.listAll());
        }
    }
}