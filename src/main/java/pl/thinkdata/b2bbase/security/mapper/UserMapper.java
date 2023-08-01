package pl.thinkdata.b2bbase.security.mapper;

import pl.thinkdata.b2bbase.security.dto.UserEditData;
import pl.thinkdata.b2bbase.security.model.User;

public class UserMapper {

    public static UserEditData mapToUserEditData(User user) {
        return new UserEditData().builder()
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .username(user.getUsername())
                .phone(user.getPhone())
                .build();
    }
}
