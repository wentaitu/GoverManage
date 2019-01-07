package com.nexuslink.govermanage.bean;

import java.util.List;

public class WorkShowBean {


    /**
     * state : 200
     * info : success
     * data : [{"id":24,"area":"2","grid":1,"log_name":"无标题","grid_admin":"用户未填写姓名","log_type":1,"edit_time":"2018-12-16 23:12:09","content":"用户未填写内容","state":1,"pic":[]},{"id":23,"area":"1","grid":1,"log_name":"无标题","grid_admin":"用户未填写姓名","log_type":1,"edit_time":"2018-12-16 23:11:58","content":"用户未填写内容","state":1,"pic":[]},{"id":22,"area":"1","grid":1,"log_name":"无标题","grid_admin":"用户未填写姓名","log_type":1,"edit_time":"2018-12-16 23:11:35","content":"用户未填写内容","state":1,"pic":[]},{"id":21,"area":"1","grid":1,"log_name":"无标题","grid_admin":"用户未填写姓名","log_type":1,"edit_time":"2018-12-16 22:51:23","content":"用户未填写内容","state":1,"pic":[]},{"id":20,"area":"222","grid":14,"log_name":"xxxxxxxxx","grid_admin":"test","log_type":2,"edit_time":"2018-12-16 22:48:26","content":"ddd","state":1,"pic":[]},{"id":19,"area":"222","grid":14,"log_name":"2018-11-11","grid_admin":"test","log_type":2,"edit_time":"2018-12-16 20:05:33","content":"ddd","state":1,"pic":["http://web.cqupt.store/public/mission/20181216/49209714c883b49f1d5b269173e67453.jpg"]},{"id":18,"area":"222","grid":14,"log_name":"2018-11-11","grid_admin":"test","log_type":2,"edit_time":"2018-12-16 20:04:56","content":"ddd","state":1,"pic":[]},{"id":17,"area":"222","grid":14,"log_name":"2018-11-11","grid_admin":"test","log_type":2,"edit_time":"2018-12-16 20:02:11","content":"ddd","state":1,"pic":[]},{"id":16,"area":"XX","grid":222,"log_name":"2018-12-3","grid_admin":"居委会主任","log_type":1,"edit_time":"2018-12-16 13:38:54","content":"2019年医保缴费宣传","state":1,"pic":[]},{"id":14,"area":"xx居委会","grid":22,"log_name":"2018-12-3","grid_admin":"居委会主任","log_type":3,"edit_time":"2018-12-16 10:55:53","content":"2019年医保缴费宣传","state":1,"pic":[]},{"id":13,"area":"222","grid":222,"log_name":"222","grid_admin":"居委会主任","log_type":1,"edit_time":"2018-12-16 10:18:56","content":"test","state":1,"pic":[]},{"id":5,"area":"222","grid":14,"log_name":"2018-11-11","grid_admin":"居委会主任","log_type":5,"edit_time":"2018-12-14 20:00:05","content":"ddd","state":1,"pic":[]},{"id":3,"area":"222","grid":14,"log_name":"2018-11-11","grid_admin":"居委会主任","log_type":1,"edit_time":"2018-12-13 21:07:46","content":"ddd","state":1,"pic":[]}]
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
         * id : 24
         * area : 2
         * grid : 1
         * log_name : 无标题
         * grid_admin : 用户未填写姓名
         * log_type : 1
         * edit_time : 2018-12-16 23:12:09
         * content : 用户未填写内容
         * state : 1
         * pic : []
         */

        private int id;
        private String area;
        private int grid;
        private String log_name;
        private String grid_admin;
        private int log_type;
        private String edit_time;
        private String content;
        private int state;
        private List<?> pic;

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

        public int getGrid() {
            return grid;
        }

        public void setGrid(int grid) {
            this.grid = grid;
        }

        public String getLog_name() {
            return log_name;
        }

        public void setLog_name(String log_name) {
            this.log_name = log_name;
        }

        public String getGrid_admin() {
            return grid_admin;
        }

        public void setGrid_admin(String grid_admin) {
            this.grid_admin = grid_admin;
        }

        public int getLog_type() {
            return log_type;
        }

        public void setLog_type(int log_type) {
            this.log_type = log_type;
        }

        public String getEdit_time() {
            return edit_time;
        }

        public void setEdit_time(String edit_time) {
            this.edit_time = edit_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public List<?> getPic() {
            return pic;
        }

        public void setPic(List<?> pic) {
            this.pic = pic;
        }
    }
}
