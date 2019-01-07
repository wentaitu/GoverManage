package com.nexuslink.govermanage.bean;

import java.util.List;

public class EventBean {


    /**
     * state : 200
     * info : success
     * data : [{"id":3,"area":"dsf","grid":"fsdf","created_at":"2018-12-15 19:08:06","title":"sdd","emergency_level":2,"emergency_type":"fsdf","from_who":5,"status":2,"state":1,"is_important":1,"content":"dsfs","emergency_source":"sdf","transfered_people":0,"is_transfered":0,"process_people":5,"from_who_name":"居委会主任","pic":[]},{"id":4,"area":"1","grid":"1","created_at":"2018-12-15 20:19:33","title":"1","emergency_level":2,"emergency_type":"1","from_who":5,"status":3,"state":1,"is_important":1,"content":"111","emergency_source":"1","transfered_people":0,"is_transfered":0,"process_people":5,"from_who_name":"居委会主任","pic":[]},{"id":5,"area":"test","grid":"test","created_at":"2018-12-15 21:39:29","title":"test","emergency_level":1,"emergency_type":"test","from_who":3,"status":2,"state":1,"is_important":0,"content":"test","emergency_source":"test","transfered_people":0,"is_transfered":0,"process_people":5,"from_who_name":"test","pic":[]},{"id":7,"area":"111","grid":"111","created_at":"2018-12-16 19:06:56","title":"111","emergency_level":1,"emergency_type":"测试","from_who":7,"status":0,"state":1,"is_important":1,"content":"111","emergency_source":"群众上报","transfered_people":0,"is_transfered":0,"process_people":0,"from_who_name":"用户未填写姓名","pic":[]},{"id":8,"area":"111","grid":"111","created_at":"2018-12-16 19:12:04","title":"111","emergency_level":1,"emergency_type":"测试","from_who":7,"status":0,"state":1,"is_important":1,"content":"111","emergency_source":"群众上报","transfered_people":0,"is_transfered":0,"process_people":0,"from_who_name":"用户未填写姓名","pic":[]},{"id":9,"area":"111","grid":"111","created_at":"2018-12-16 19:12:22","title":"111","emergency_level":1,"emergency_type":"0","from_who":7,"status":0,"state":1,"is_important":1,"content":"111","emergency_source":"群众上报","transfered_people":0,"is_transfered":0,"process_people":0,"from_who_name":"用户未填写姓名","pic":[]},{"id":10,"area":"111","grid":"111","created_at":"2018-12-16 19:14:04","title":"111","emergency_level":1,"emergency_type":"0","from_who":7,"status":0,"state":1,"is_important":1,"content":"111","emergency_source":"群众上报","transfered_people":0,"is_transfered":0,"process_people":0,"from_who_name":"用户未填写姓名","pic":[]},{"id":11,"area":"0","grid":"2","created_at":"2113-04-16 19:48:45","title":"xxxxxxxxxxx","emergency_level":0,"emergency_type":"0","from_who":7,"status":0,"state":1,"is_important":0,"content":"xxxxxxxxxxx","emergency_source":"0","transfered_people":0,"is_transfered":0,"process_people":0,"from_who_name":"用户未填写姓名","pic":[]},{"id":12,"area":"0","grid":"2","created_at":"2113-04-16 19:48:45","title":"xxxxxxxxxxx","emergency_level":0,"emergency_type":"0","from_who":7,"status":0,"state":1,"is_important":0,"content":"xxxxxxxxxxx","emergency_source":"0","transfered_people":0,"is_transfered":0,"process_people":0,"from_who_name":"用户未填写姓名","pic":[]},{"id":13,"area":"111","grid":"111","created_at":"2018-12-16 19:35:27","title":"111","emergency_level":1,"emergency_type":"0","from_who":7,"status":0,"state":1,"is_important":1,"content":"111","emergency_source":"群众上报","transfered_people":0,"is_transfered":0,"process_people":0,"from_who_name":"用户未填写姓名","pic":[]},{"id":14,"area":"111","grid":"111","created_at":"2018-12-16 19:37:25","title":"111","emergency_level":1,"emergency_type":"测试","from_who":5,"status":0,"state":1,"is_important":1,"content":"111","emergency_source":"群众上报","transfered_people":0,"is_transfered":0,"process_people":0,"from_who_name":"居委会主任","pic":["http://web.cqupt.store/public/mission/20181216/c66573cf8307ed8307c9b42d451bbb8a.jpg"]},{"id":15,"area":"111","grid":"111","created_at":"2018-12-16 19:37:48","title":"111","emergency_level":1,"emergency_type":"测试","from_who":5,"status":0,"state":1,"is_important":1,"content":"111","emergency_source":"群众上报","transfered_people":0,"is_transfered":0,"process_people":0,"from_who_name":"居委会主任","pic":[]},{"id":16,"area":"111","grid":"111","created_at":"2018-12-16 19:37:53","title":"111","emergency_level":1,"emergency_type":"测试","from_who":5,"status":0,"state":1,"is_important":1,"content":"111","emergency_source":"群众上报","transfered_people":0,"is_transfered":0,"process_people":0,"from_who_name":"居委会主任","pic":[]},{"id":17,"area":"111","grid":"111","created_at":"2018-12-16 19:38:47","title":"111","emergency_level":1,"emergency_type":"0","from_who":7,"status":0,"state":1,"is_important":1,"content":"111","emergency_source":"群众上报","transfered_people":0,"is_transfered":0,"process_people":0,"from_who_name":"用户未填写姓名","pic":["http://web.cqupt.store/public/mission/20181216/664083497193a5a917cca4746577e569.png"]},{"id":18,"area":"111","grid":"111","created_at":"2018-12-16 19:44:21","title":"111","emergency_level":1,"emergency_type":"0","from_who":7,"status":0,"state":1,"is_important":1,"content":"111","emergency_source":"群众上报","transfered_people":0,"is_transfered":0,"process_people":0,"from_who_name":"用户未填写姓名","pic":[]},{"id":19,"area":"0","grid":"2","created_at":"2135-12-15 14:43:32","title":"xxxxxxxxxxx","emergency_level":0,"emergency_type":"1","from_who":7,"status":0,"state":1,"is_important":0,"content":"xxxxxxxxxxx","emergency_source":"2","transfered_people":0,"is_transfered":0,"process_people":0,"from_who_name":"用户未填写姓名","pic":[]},{"id":20,"area":"0","grid":"2","created_at":"2135-12-15 14:43:32","title":"xxxxxxxxxxx","emergency_level":0,"emergency_type":"1","from_who":7,"status":0,"state":1,"is_important":0,"content":"xxxxxxxxxxx","emergency_source":"2","transfered_people":0,"is_transfered":0,"process_people":0,"from_who_name":"用户未填写姓名","pic":[]},{"id":21,"area":"0","grid":"2","created_at":"2135-12-15 14:43:32","title":"xxxxxxxxxxx","emergency_level":0,"emergency_type":"1","from_who":7,"status":0,"state":1,"is_important":0,"content":"xxxxxxxxxxx","emergency_source":"2","transfered_people":0,"is_transfered":0,"process_people":0,"from_who_name":"用户未填写姓名","pic":["http://web.cqupt.store/public/mission/20181216/893faf2bb3bae7d607fb91f1427c1d31.jpeg"]}]
     */

    private int state;
    private String info;
    private List<DataBean> data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * area : dsf
         * grid : fsdf
         * created_at : 2018-12-15 19:08:06
         * title : sdd
         * emergency_level : 2
         * emergency_type : fsdf
         * from_who : 5
         * status : 2   0-3
         * state : 1
         * is_important : 1
         * content : dsfs
         * emergency_source : sdf
         * transfered_people : 0
         * is_transfered : 0
         * process_people : 5
         * from_who_name : 居委会主任
         * pic : []
         */

        private int id;
        private String area;
        private String grid;
        private String created_at;
        private String title;
        private int emergency_level;
        private String emergency_type;
        private int from_who;
        private int status;
        private int state;
        private int is_important;
        private String content;
        private String emergency_source;
        private int transfered_people;
        private int is_transfered;
        private int process_people;
        private String from_who_name;
        private List<String> pic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getGrid() {
            return grid;
        }

        public void setGrid(String grid) {
            this.grid = grid;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getEmergency_level() {
            return emergency_level;
        }

        public void setEmergency_level(int emergency_level) {
            this.emergency_level = emergency_level;
        }

        public String getEmergency_type() {
            return emergency_type;
        }

        public void setEmergency_type(String emergency_type) {
            this.emergency_type = emergency_type;
        }

        public int getFrom_who() {
            return from_who;
        }

        public void setFrom_who(int from_who) {
            this.from_who = from_who;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getIs_important() {
            return is_important;
        }

        public void setIs_important(int is_important) {
            this.is_important = is_important;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getEmergency_source() {
            return emergency_source;
        }

        public void setEmergency_source(String emergency_source) {
            this.emergency_source = emergency_source;
        }

        public int getTransfered_people() {
            return transfered_people;
        }

        public void setTransfered_people(int transfered_people) {
            this.transfered_people = transfered_people;
        }

        public int getIs_transfered() {
            return is_transfered;
        }

        public void setIs_transfered(int is_transfered) {
            this.is_transfered = is_transfered;
        }

        public int getProcess_people() {
            return process_people;
        }

        public void setProcess_people(int process_people) {
            this.process_people = process_people;
        }

        public String getFrom_who_name() {
            return from_who_name;
        }

        public void setFrom_who_name(String from_who_name) {
            this.from_who_name = from_who_name;
        }

        public List<String> getPic() {
            return pic;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }
    }
}
