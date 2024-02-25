package notebook.model.repository;
import notebook.model.User;
import java.util.List;
import java.util.Optional;

public interface GBRepository {
    List<User> findAll();
    User create(User user);
    Optional<User> findById(Long id);
    Optional<User> update(Long userId, User update);
    Optional<User> delete(Long userId, User delete);
    List<String> readAll();

    void saveAll(List<String> data);
    void write(List<User> users);
}
