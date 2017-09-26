package com.shyms.farendating.model;

import java.util.List;

/**
 * Created by hokas on 2015/8/7.
 */
public class ImageInfo {

    /**
     * code : 0
     * data : {"images":[{"img":"图片base64后的字符串.........","GuId":"344433222","Type":"jpg"},{"img":"图片base64后的字符串.........","GuId":"344433222","Type":"jpg"},{"img":"图片base64后的字符串.........","GuId":"344433222","Type":"jpg"},{"img":"图片base64后的字符串.........","GuId":"344433222","Type":"jpg"}]}
     * message : null
     * meta : null
     */

    private String code;
    private DataEntity data;
    private Object message;
    private Object meta;

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public void setMeta(Object meta) {
        this.meta = meta;
    }

    public String getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public Object getMessage() {
        return message;
    }

    public Object getMeta() {
        return meta;
    }

    public static class DataEntity {
        /**
         * images : [{"img":"图片base64后的字符串.........","GuId":"344433222","Type":"jpg"},{"img":"图片base64后的字符串.........","GuId":"344433222","Type":"jpg"},{"img":"图片base64后的字符串.........","GuId":"344433222","Type":"jpg"},{"img":"图片base64后的字符串.........","GuId":"344433222","Type":"jpg"}]
         */

        private List<ImagesEntity> images;

        public void setImages(List<ImagesEntity> images) {
            this.images = images;
        }

        public List<ImagesEntity> getImages() {
            return images;
        }

        public static class ImagesEntity {
            /**
             * img : 图片base64后的字符串.........
             * GuId : 344433222
             * Type : jpg
             */

            private String img;
            private String GuId;
            private String Type;

            public void setImg(String img) {
                this.img = img;
            }

            public void setGuId(String GuId) {
                this.GuId = GuId;
            }

            public void setType(String Type) {
                this.Type = Type;
            }

            public String getImg() {
                return img;
            }

            public String getGuId() {
                return GuId;
            }

            public String getType() {
                return Type;
            }

            @Override
            public String toString() {
                return "ImagesEntity{" +
                        "img='" + img + '\'' +
                        ", GuId='" + GuId + '\'' +
                        ", Type='" + Type + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "images=" + images +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message=" + message +
                ", meta=" + meta +
                '}';
    }
}
