package com.nexuslink.govermanage.bean;

import java.util.List;

public class GridBean {
    /**
     * state : 200
     * info : success
     * data : [{"id":1,"name":"代家沟组","area":"0","service_area":"代家沟","state":1,"num":0},{"id":2,"name":"自生桥组","area":"0","service_area":"自生桥","state":1,"num":0},{"id":3,"name":"三才生组","area":"0","service_area":"三才生","state":1,"num":2},{"id":4,"name":"土地垭组","area":"0","service_area":"土地垭","state":1,"num":0}]
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
         * id : 1
         * name : 代家沟组
         * area : 0
         * service_area : 代家沟
         * state : 1
         * num : 0
         */

        private int id;
        private String name;
        private String area;
        private String service_area;
        private int state;
        private int num;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getService_area() {
            return service_area;
        }

        public void setService_area(String service_area) {
            this.service_area = service_area;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
