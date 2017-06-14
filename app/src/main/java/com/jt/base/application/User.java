package com.jt.base.application;

/**
 * Created by m1762 on 2017/6/13.
 */

public class User {

    /**
     * code : 0
     * msg : success
     * result : {"user":{"uid":4,"grade":0,"phone":"17625017026","head":null,"username":"17625017026","sex":null,"password":"888888","email":null,"profession":null,"birthday":null,"status":1,"created":"2017-06-12 16:07:23","updatetime":null,"token":"FAD22443CA765A9BC81585BF67E41829"},"token":"FAD22443CA765A9BC81585BF67E41829"}
     * page : null
     */

    private int code;
    private String msg;
    private ResultBean result;
    private Object page;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }

    public static class ResultBean {
        /**
         * user : {"uid":4,"grade":0,"phone":"17625017026","head":null,"username":"17625017026","sex":null,"password":"888888","email":null,"profession":null,"birthday":null,"status":1,"created":"2017-06-12 16:07:23","updatetime":null,"token":"FAD22443CA765A9BC81585BF67E41829"}
         * token : FAD22443CA765A9BC81585BF67E41829
         */

        private UserBean user;
        private String token;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class UserBean {
            /**
             * uid : 4
             * grade : 0
             * phone : 17625017026
             * head : null
             * username : 17625017026
             * sex : null
             * password : 888888
             * email : null
             * profession : null
             * birthday : null
             * status : 1
             * created : 2017-06-12 16:07:23
             * updatetime : null
             * token : FAD22443CA765A9BC81585BF67E41829
             */

            private int uid;
            private int grade;
            private String phone;
            private Object head;
            private String username;
            private Object sex;
            private String password;
            private Object email;
            private Object profession;
            private Object birthday;
            private int status;
            private String created;
            private Object updatetime;
            private String token;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getGrade() {
                return grade;
            }

            public void setGrade(int grade) {
                this.grade = grade;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getHead() {
                return head;
            }

            public void setHead(Object head) {
                this.head = head;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public Object getSex() {
                return sex;
            }

            public void setSex(Object sex) {
                this.sex = sex;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public Object getProfession() {
                return profession;
            }

            public void setProfession(Object profession) {
                this.profession = profession;
            }

            public Object getBirthday() {
                return birthday;
            }

            public void setBirthday(Object birthday) {
                this.birthday = birthday;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public Object getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(Object updatetime) {
                this.updatetime = updatetime;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }
    }
}
