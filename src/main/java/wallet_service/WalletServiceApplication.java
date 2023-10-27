package wallet_service;

import jakarta.servlet.Servlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import wallet_service.out.controller.HistoryController;
import wallet_service.out.controller.PlayerController;
import wallet_service.out.controller.TransactionController;
import wallet_service.out.controller.TransactionHistoryController;
import wallet_service.out.service.PlayerService;
import wallet_service.out.service.PlayerServiceImpl;

public class WalletServiceApplication {

    public static void main(String[] args) throws Exception {

        // Запускаем встроенный сервер
        try {
            Server server = new Server(8080);
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");
            server.setHandler(context);

            // Создание сервиса и передача его контроллерам
            PlayerService playerService = new PlayerServiceImpl();

            context.addServlet(new ServletHolder((Servlet) new PlayerController(playerService)), "/player/*");
            context.addServlet(new ServletHolder((Servlet) new HistoryController(playerService)), "/player/history");
            context.addServlet(new ServletHolder((Servlet) new TransactionController(playerService)), "/transaction");
            context.addServlet(new ServletHolder((Servlet) new TransactionHistoryController(playerService)), "/transaction/history");

            server.start();
            server.join();
        } catch (Exception exception) {
            System.err.println("При запуске сервера произошла ошибка: " + exception.getMessage());
            exception.printStackTrace();
        }

    }
}
