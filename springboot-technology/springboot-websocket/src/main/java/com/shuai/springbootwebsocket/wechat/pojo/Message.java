package com.shuai.springbootwebsocket.wechat.pojo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "message") // 指定表的名称
@Builder
public class Message {

    @Id
    private ObjectId id;
    private String msg;
    /**
     * 消息状态，1-未读，2-已读
     */
    @Indexed
    private Integer status;
    @Field("send_date")
    @Indexed
    private Date sendDate;
    @Field("read_date")
    private Date readDate;
    @Indexed
    private User from;
    @Indexed
    private User to;

    public static void main(String[] args) {
        Message message1 = new Message();
        message1.setMsg("shuai");
        Message message = Message.builder()
            .id(ObjectId.get())
            .msg("你好")
            .sendDate(new Date())
            .status(1)
            .from(new User(1001L, "zhangsan"))
            .to(new User(1002L, "lisi"))
            .build();
        message = Message.builder()
            .id(ObjectId.get())
            .msg("我在学习开发IM")
            .sendDate(new Date())
            .status(1)
            .from(new User(1001L, "zhangsan"))
            .to(new User(1002L, "lisi"))
            .build();
        System.out.println(message1);
        System.out.println(message);
    }
}
