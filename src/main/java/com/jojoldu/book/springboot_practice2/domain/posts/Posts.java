package com.jojoldu.book.springboot_practice2.domain.posts;

import com.jojoldu.book.springboot_practice2.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 롬복 어노테이션. 필수 어노테이션들은 아니다. 코드를 단순화시켜줌
@Getter // 클래스 내 모든 필드의 Getter 메소드 자동 생성
@NoArgsConstructor  // 기본 생성자 자동 추가. 여기선 public Posts() {} 와 같은 효과
@Entity // JPA의 어노테이션. 카멜케이스 이름을 언더스코어 네이밍하여 DB 테이블 이름을 정해준다.
public class Posts extends BaseTimeEntity {    // 실제 DB 테이블과 매칭될 클래스이다. 주로 Entity클래스라고 한다.
    // JPA 사용 시 DB에 작업할 경우 실제 쿼리를 보내기 보다, 엔티티 클래스의 수정을 통해 작업한다.
    // Entity 클래스에서는 절대 Setter를 만들지 않도록 한다. 대신 값을 변경해야 할 경우, 목적과 의도를 명확히 나타낼 수 있는 메소드를 추가한다.

    @Id // 테이블의 PK필드를 의미한다. JPA 어노테이션.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성 규칙을 나타낸다. GenerationType.IDENTITY 옵션을 추가하면 Auto_Increment가 된다.
    private Long id;

    // Column 어노테이션은 테이블의 칼럼을 나타낸다. 굳이 사용하지 않아도 클래스의 필드는 모두 칼럼이 되지만, 사용하는 이유는 옵션 사용을 위해서이다.
    // 문자열의 사이즈를 기본값 250이 아닌 값으로 변경하거나, 타입을 변경하고 싶은 경우 등에 사용한다. JPA 어노테이션.
    @Column(length = 500, nullable = false) 
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder    // 롬복 어노테이션. 해당 클래스의 빌더 패턴 클래스 생성. 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
