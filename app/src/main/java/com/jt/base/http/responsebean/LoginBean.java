package com.jt.base.http.responsebean;

/**
 * Created by m1762 on 2017/6/12.
 */

public class LoginBean {

    /**
     * code : 0
     * msg : success
     * result : {"user":{"uid":2,"grade":0,"phone":"18115756528","head":"1111","username":"Orchid","sex":1,"password":"123456","email":"555@qq.com","profession":"1111","birthday":"2017-06-10 11:02:27","status":1,"created":"2017-06-10 11:04:32","updatetime":null,"token":"10086"},"token":"10086"}
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
         * user : {"uid":2,"grade":0,"phone":"18115756528","head":"1111","username":"Orchid","sex":1,"password":"123456","email":"555@qq.com","profession":"1111","birthday":"2017-06-10 11:02:27","status":1,"created":"2017-06-10 11:04:32","updatetime":null,"token":"10086"}
         * token : 10086
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
             * uid : 2
             * grade : 0
             * phone : 18115756528
             * head : 1111
             * username : Orchid
             * sex : 1
             * password : 123456
             * email : 555@qq.com
             * profession : 1111
             * birthday : 2017-06-10 11:02:27
             * status : 1
             * created : 2017-06-10 11:04:32
             * updatetime : null
             * token : 10086
             */

            private int uid;
            private int grade;
            private String phone;
            private String head;
            private String username;
            private int sex;
            private String password;
            private String email;
            private String profession;
            private String birthday;
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

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getProfession() {
                return profession;
            }

            public void setProfession(String profession) {
                this.profession = profession;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
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
