package hello;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by OZON3 on 25/08/2019.
 */
@Service
public class UserLogService {

    @Autowired
    private UserLogRepository userLogRepository;


    public List<UserLog> getAll(){
        return userLogRepository.findAll();
    }

    public Page<UserLog> getAllPaged(PageRequest pageRequest){
        return userLogRepository.findAll(pageRequest);
    }

    public UserLog save(UserLogApiModel model){
        UserLog userLog = new UserLog();
        BeanUtils.copyProperties(model, userLog);

        return userLogRepository.save(userLog);
    }
}

