package wallet_service.out.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import wallet_service.out.util.JwtProvider;

import java.io.IOException;


/**
 * @author Олег Тодор
 * Класс JwtAuthFilter реализует интерфейс Filter и предназначен
 * для проверки и валидации JWT-токена, содержащегося в заголовке http-запроса.
 * В случае успешной валидации токена, добавляет в request имя пользователя из токена.
 */
public class JwtAuthFilter implements Filter {

    // Провайдер для JWT токенов.
    private JwtProvider jwtProvider;

    /**
     * Конструктор инициализирует экземпляр JwtProvider.
     */
    public JwtAuthFilter() {
        this.jwtProvider = new JwtProvider();
    }

    /**
     * Этот метод проверяет наличие JWT-токена в запросе и валидирует его.
     * Если токен отсутствует или не проходит валидацию, то клиенту возвращается ответ с HTTP статусом 401.
     * В случае валидного токена, имя пользователя из токена добавляется в объект запроса.
     *
     * @param servletRequest  запрос клиента
     * @param servletResponse ответ сервера
     * @param filterChain     цепочка, которой следует выполнить фильтр
     *
     * @throws IOException возможное исключение ввода/вывода
     * @throws ServletException если произошла ошибка обработки запроса
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String jwtToken = req.getHeader("YourSecretKey");
        if (jwtToken == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String username;
        try {
            username = jwtProvider.validateToken(jwtToken);
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        req.setAttribute("username", username);

        filterChain.doFilter(req, resp);
    }
}
