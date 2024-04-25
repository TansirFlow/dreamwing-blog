package com.dreamwing.service.impl;

import com.dreamwing.constants.EmailConstants;
import com.dreamwing.constants.UserConstants;
import com.dreamwing.constants.VerifyCodeConstants;
import com.dreamwing.exception.VerifyCodeException;
import com.dreamwing.mapper.OtherVerifyCodeMapper;
import com.dreamwing.mapper.RegisterVerifyCodeMapper;
import com.dreamwing.mapper.UserMapper;
import com.dreamwing.pojo.EmailRequest;
import com.dreamwing.pojo.OtherVerifyCode;
import com.dreamwing.pojo.RegisterVerifyCode;
import com.dreamwing.pojo.User;
import com.dreamwing.service.EmailService;
import com.dreamwing.service.UserService;
import com.dreamwing.utils.EmailRequestUtil;
import com.dreamwing.utils.Md5Util;
import com.dreamwing.utils.ThreadLocalUtil;
import com.dreamwing.utils.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RegisterVerifyCodeMapper registerVerifyCodeMapper;
    @Autowired
    private OtherVerifyCodeMapper otherVerifyCodeMapper;

    @Autowired
    private EmailService emailService;

    /**
     * 用户注册
     * 验证码的存在、正确性、时效性、用完删除
     *
     * @param user
     */
    @Override
    public void register(User user) {
        RegisterVerifyCode registerVerifyCode = registerVerifyCodeMapper.find(user.getUsername(), user.getEmail());
        if (registerVerifyCode == null) {
            throw new VerifyCodeException(VerifyCodeConstants.REGISTER_VERIFY_CODE_NOT_EXIST);
        }
        if (!Objects.equals(user.getVerifyCode(), registerVerifyCode.getCode())) {
            throw new VerifyCodeException(VerifyCodeConstants.REGISTER_VERIFY_CODE_ERROR);
        }
        if (!registerVerifyCode.getExpirationTime().isAfter(LocalDateTime.now())) {
            registerVerifyCodeMapper.delete(user.getUsername(), user.getEmail());
            throw new VerifyCodeException(VerifyCodeConstants.REGISTER_VERIFY_CODE_EXPIRATION);
        }
        Integer nums = userMapper.getUserNum();
        if (nums == 0) {
            user.setRole(UserConstants.DEFAULT_FIRST_USER_ROLE);
        } else {
            user.setRole(UserConstants.DEFAULT_USER_ROLE);
        }
        String md5Password = Md5Util.getMD5String(user.getInputPassword());
        user.setPassword(md5Password);
        user.setState(UserConstants.USER_STATE_NORMAL);
        userMapper.register(user);
        registerVerifyCodeMapper.delete(user.getUsername(), user.getEmail());
    }

    /**
     * 根据username查询用户信息
     *
     * @param username
     * @return
     */
    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    /**
     * 更新用户密码
     *
     * @param newPwd
     * @param id
     */
    @Override
    public void updatePassword(String newPwd, Integer id) {
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
    }

    /**
     * 更新用户头像
     *
     * @param avatarUrl
     */
    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        userMapper.updateAvatar(avatarUrl, id);
    }

    /**
     * 获取用户信息
     * 屏蔽密码
     *
     * @return
     */
    @Override
    public User getUserInfo() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        return userMapper.findByUserName(username);
    }

    /**
     * 发送用于注册的邮箱验证码
     *
     * @param username
     * @param email
     */
    @Override
    public void sendRegisterVerifyCodeEmail(String username, String email) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setSubject(EmailConstants.DEFAULT_EMAIL_SUBJECT);
        emailRequest.setRecipient(email);
        String verifyCode = VerifyCode.makeVerifyCode();
        RegisterVerifyCode registerVerifyCode = registerVerifyCodeMapper.find(username, email);
        if (registerVerifyCode == null) {
            registerVerifyCodeMapper.add(username, email, verifyCode);
        } else {
            registerVerifyCodeMapper.update(username, email, verifyCode);
        }

        emailRequest.setMsgBody("您的验证码是" + verifyCode);
        emailService.sendEmail(emailRequest);
    }

    /**
     * 发送找回密码邮件
     *
     * @param username
     * @param email
     */
    @Override
    public void sendFindPwdEmail(String username, String email) {
        String verifyCode = VerifyCode.makeVerifyCode();
        String type = VerifyCodeConstants.FIND_PASSWORD_VERIFY_CODE;
        OtherVerifyCode otherVerifyCode = otherVerifyCodeMapper.find(username, type);
        if (otherVerifyCode == null) {
            otherVerifyCodeMapper.add(username, type, verifyCode);
        } else {
            otherVerifyCodeMapper.update(username, type, verifyCode);
        }
        emailService.sendEmail(EmailRequestUtil.getEmailRequest(
                EmailConstants.DEFAULT_EMAIL_SUBJECT, email, "您的验证码是" + verifyCode
        ));
    }

    @Override
    public void findPwd(User user) {
        OtherVerifyCode otherVerifyCode = otherVerifyCodeMapper.find(user.getUsername(), VerifyCodeConstants.FIND_PASSWORD_VERIFY_CODE);
        if (otherVerifyCode == null || !Objects.equals(otherVerifyCode.getCode(), user.getVerifyCode())) {
            throw new VerifyCodeException(VerifyCodeConstants.OTHER_VERIFY_CODE_NOT_EXIST);
        }
        if(!otherVerifyCode.getExpirationTime().isAfter(LocalDateTime.now())){
            otherVerifyCodeMapper.delete(user.getUsername(),VerifyCodeConstants.FIND_PASSWORD_VERIFY_CODE);
            throw new VerifyCodeException(VerifyCodeConstants.OTHER_VERIFY_CODE_EXPIRATION);
        }
        User u=userMapper.findByUserName(user.getUsername());
        userMapper.updatePwd(Md5Util.getMD5String(user.getInputPassword()),u.getId());
        otherVerifyCodeMapper.delete(user.getUsername(),VerifyCodeConstants.FIND_PASSWORD_VERIFY_CODE);
    }

    /**
     * 修改用户基本信息
     *
     * @param user
     */
    @Override
    public void update(User user) {
        userMapper.update(user);
    }
}
