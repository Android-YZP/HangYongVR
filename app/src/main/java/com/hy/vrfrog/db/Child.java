/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hy.vrfrog.db;


import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

/**
 * Author: wyouflf
 * Date: 13-7-29
 * Time: 下午5:04
 */
@Table(name = "child")
public class Child {

    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "videoname")
    private String videoname;

    @Column(name = "img")
    private String img;

    @Column(name = "video_url")
    private String url;

    @Column(name = "img_url")
    private String url1;

    @Column(name = "percentage")
    private String num;

    @Column(name = "parentId" /*, property = "UNIQUE"//如果是一对一加上唯一约束*/)
    private long parentId; // 外键表id

    // 这个属性被忽略，不存入数据库
    private String willIgnore;

    @Column(name = "text")
    private String text;


    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideoName() {
        return videoname;
    }

    public void setVideoName(String videoname) {
        this.videoname = videoname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl1() {
        return url;
    }

    public void setUrl1(String url) {
        this.url = url;
    }

    public String getWillIgnore() {
        return willIgnore;
    }

    public void setWillIgnore(String willIgnore) {
        this.willIgnore = willIgnore;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", name='" + videoname + '\'' +
                ", url='" + url + '\'' +
                ", url1='" + url + '\'' +
                ", parentId=" + parentId +
                ", willIgnore='" + willIgnore + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
