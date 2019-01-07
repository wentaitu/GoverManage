package com.nexuslink.govermanage.bean;

public class UploadFaildBean {

    /**
     * status : Upload Failed
     * msg : {"file":{"name":"photo","type":"application/octet-stream","tmp_name":"/tmp/phpRUcYy9","error":0,"size":101506}}
     */

    private String status;
    private MsgBean msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * file : {"name":"photo","type":"application/octet-stream","tmp_name":"/tmp/phpRUcYy9","error":0,"size":101506}
         */

        private FileBean file;

        public FileBean getFile() {
            return file;
        }

        public void setFile(FileBean file) {
            this.file = file;
        }

        public static class FileBean {
            /**
             * name : photo
             * type : application/octet-stream
             * tmp_name : /tmp/phpRUcYy9
             * error : 0
             * size : 101506
             */

            private String name;
            private String type;
            private String tmp_name;
            private int error;
            private int size;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTmp_name() {
                return tmp_name;
            }

            public void setTmp_name(String tmp_name) {
                this.tmp_name = tmp_name;
            }

            public int getError() {
                return error;
            }

            public void setError(int error) {
                this.error = error;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }
        }
    }
}
