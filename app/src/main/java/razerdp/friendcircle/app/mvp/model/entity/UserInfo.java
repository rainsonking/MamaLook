package razerdp.friendcircle.app.mvp.model.entity;

import cn.bmob.v3.BmobObject;
import razerdp.github.com.baselibrary.utils.EncryUtil;

/**
 * Created by 大灯泡 on 2016/10/27.
 * <p>
 * 用户
 */

public class UserInfo extends BmobObject {

    public interface UserFields {
        String USERNAME = "username";
        String PASSWORD = "password";
        String NICK = "nick";
        String AUTHOR_USER = "author";
        String AVATAR = "avatar";
        String COVER="cover";
    }

    private String username;
    private String password;
    private String nick;
    private String avatar;
    private String cover;

    public UserInfo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return getObjectId();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = EncryUtil.MD5(password);
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nick='" + nick + '\'' +
                ", avatar='" + avatar + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
