package com.dream.softwarecupspring.Service.ServiceImpl;

import com.dream.softwarecupspring.Mapper.PublicMapper;
import com.dream.softwarecupspring.Service.PublicService;
import com.dream.softwarecupspring.pojo.User.LoginInfo;
import com.dream.softwarecupspring.pojo.Common.ResetQueryParam;
import com.dream.softwarecupspring.pojo.User.User;
import com.dream.softwarecupspring.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PublicServiceImpl implements PublicService {
    @Autowired
    private PublicMapper publicMapper;

    @Override
    public LoginInfo login(User user) {
        User u = publicMapper.selectByUsernameAndPassword(user);
        if (u == null) {
            return null;
        }
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", u.getId());
        claims.put("username", u.getUsername());
        String jwt = JwtUtils.generateToken(claims);
        return new LoginInfo(u.getId(),u.getUsername(),u.getName(),u.getRole(),u.getClassName(),jwt);
    }

    @Override
    public Integer reset(ResetQueryParam resetQueryParam) {
        if (resetQueryParam.getIdentifier() == null || resetQueryParam.getIdentifier().isEmpty()) {
            return publicMapper.updatePassword(resetQueryParam);
        }
        else {
            return publicMapper.updatePasswordByIdentifier(resetQueryParam);
        }
    }
}
