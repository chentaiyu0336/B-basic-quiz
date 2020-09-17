package com.example.demo.repository;

import com.example.demo.domain.Education;
import com.example.demo.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    // GTB: - userList 叫 users 就行
    // GTB: 为什么不用 Map？
    private static List<User> userList = new ArrayList<>();

    {
        userList.add(new User(1, "KAMIL", 24,
                "https://inews.gtimg.com/newsapp_match/0/3581582328/0",
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellendus, non, dolorem, cumque distinctio magni quam expedita velit laborum sunt amet facere tempora ut fuga aliquam ad asperiores voluptatem dolorum! Quasi.",
                null));
    }

    // GTB: - 这个 endId 也有点怪，叫 userId、nextUserId 啥的都行
    static long endId = 1;

    public void isUserExit(long id) {
        // GTB: - 不要用 flag 啦，直接判断抛异常
        // GTB: - 了解一下 Stream API，很多时候直接替代 for

//        下面两段，任选一段，都比目前实现的要好一点

//        userList.stream()
//                .filter(user -> user.getId() == id)
//                .findFirst()
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

//        for (User user : userList) {
//            if (user.getId() == id) {
//                return;
//            }
//        }
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");

        boolean flg = false;
        for (User user : userList) {
            if (user.getId() == id)
                flg = true;
        }
        if (!flg)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
    }

    public User getUserById(long id) {
        isUserExit(id);
        // GTB: - orElseThrow() 了解一下
        return userList.stream().filter(user -> user.getId() == id).findFirst().get();
    }

    public long addUser(User user) {
        // GTB: - 这样生成 id 是有线程安全问题的，跟小组同学一起讨论一下
        endId++;
        user.setId(endId);
        userList.add(user);
        return endId;
    }

    public void addEducation(long id, Education education) {
        education.setUserId(id);
        isUserExit(id);
        userList.forEach(user -> {
            if (user.getId() == id) {
                // GTB: - 这里处理有点繁琐，想想有没有其它办法，让这个方法更简洁
                if (user.getEducationList() == null)
                    user.setEducationList(new ArrayList<>());
                user.getEducationList().add(education);
            }
        });
    }

    public List<Education> getEducationList(long id) {
        isUserExit(id);
        return userList.stream().filter(user -> user.getId() == id).findFirst().get().getEducationList();
    }
}
