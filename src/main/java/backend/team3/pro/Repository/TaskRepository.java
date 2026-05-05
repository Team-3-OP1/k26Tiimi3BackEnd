package backend.team3.pro.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.team3.pro.Model.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByAssignedUser_Username(String username);
}
