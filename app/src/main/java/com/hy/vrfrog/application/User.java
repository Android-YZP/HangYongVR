package com.hy.vrfrog.application;

/**
 * Created by m1762 on 2017/6/13.
 */

public class User {


    /**
     * code : 0
     * msg : success
     * result : {"user":{"uid":66,"phone":"18626361403","account":"18626361403","head":null,"username":"18626361403","sex":null,"password":"123456","email":null,"profession":null,"birthday":null,"status":1,"created":null,"updatetime":null,"token":"739F8D20A4A6083BBEBFE32F32A5561F","rid":0,"roleName":null,"money":0,"role":null},"usersig":"eJxlkFFPgzAUhd-3KwivM1paSsFkD0XocM5Np9O4F0JGgRsi60qnbsb-7sQlkvj8ffecnPs5sCzLfpw*nGfr9WbXmNTslbStS8tG9tkfVAryNDMp0fk-KD8UaJlmhZG6gw6lFCPUdyCXjYECTgYjgfAjjLjLPeSTMIxDERMsCOaUeo7oXbZ5nXb1v9HuMZcwTJy*AmUHb*P7q2txpycV2c5nq0V40R5URKJYvSQTb5-o4fgJhm1GV3U*X5AbDiGT46UX8KLaJfVzVNZ*cKho0gi2DaYEU70EKAyfee*sHI16lQZe5Wkrwgw5GLk9*iZ1C5umEzBy6BEH6Ochg6-BN-HRYUQ_","token":"739F8D20A4A6083BBEBFE32F32A5561F"}
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
         * user : {"uid":66,"phone":"18626361403","account":"18626361403","head":null,"username":"18626361403","sex":null,"password":"123456","email":null,"profession":null,"birthday":null,"status":1,"created":null,"updatetime":null,"token":"739F8D20A4A6083BBEBFE32F32A5561F","rid":0,"roleName":null,"money":0,"role":null}
         * usersig : eJxlkFFPgzAUhd-3KwivM1paSsFkD0XocM5Np9O4F0JGgRsi60qnbsb-7sQlkvj8ffecnPs5sCzLfpw*nGfr9WbXmNTslbStS8tG9tkfVAryNDMp0fk-KD8UaJlmhZG6gw6lFCPUdyCXjYECTgYjgfAjjLjLPeSTMIxDERMsCOaUeo7oXbZ5nXb1v9HuMZcwTJy*AmUHb*P7q2txpycV2c5nq0V40R5URKJYvSQTb5-o4fgJhm1GV3U*X5AbDiGT46UX8KLaJfVzVNZ*cKho0gi2DaYEU70EKAyfee*sHI16lQZe5Wkrwgw5GLk9*iZ1C5umEzBy6BEH6Ochg6-BN-HRYUQ_
         * token : 739F8D20A4A6083BBEBFE32F32A5561F
         */

        private UserBean user;
        private String usersig;
        private String token;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getUsersig() {
            return usersig;
        }

        public void setUsersig(String usersig) {
            this.usersig = usersig;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class UserBean {
            /**
             * uid : 66
             * phone : 18626361403
             * account : 18626361403
             * head : null
             * username : 18626361403
             * sex : null
             * password : 123456
             * email : null
             * profession : null
             * birthday : null
             * status : 1
             * created : null
             * updatetime : null
             * token : 739F8D20A4A6083BBEBFE32F32A5561F
             * rid : 0
             * roleName : null
             * money : 0
             * role : null
             */

            private int uid;
            private String phone;
            private String account;
            private Object head;
            private String username;
            private Object sex;
            private String password;
            private Object email;
            private Object profession;
            private Object birthday;
            private int status;
            private Object created;
            private Object updatetime;
            private String token;
            private int rid;
            private Object roleName;
            private int money;
            private Object role;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
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

            public Object getCreated() {
                return created;
            }

            public void setCreated(Object created) {
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

            public int getRid() {
                return rid;
            }

            public void setRid(int rid) {
                this.rid = rid;
            }

            public Object getRoleName() {
                return roleName;
            }

            public void setRoleName(Object roleName) {
                this.roleName = roleName;
            }

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }

            public Object getRole() {
                return role;
            }

            public void setRole(Object role) {
                this.role = role;
            }
        }
    }
}
