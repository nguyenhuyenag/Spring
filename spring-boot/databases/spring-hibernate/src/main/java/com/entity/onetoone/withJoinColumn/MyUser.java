package com.entity.onetoone.withJoinColumn;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * - Hoặc là with Foreign Key
 * 
 * - Cách quan hệ 1 - 1 trong cơ sở dữ liệu biểu thị rằng một thực thể A tương
 * ứng với một thực thể B và ngược lại. Ví dụ một người sẽ có một địa chỉ duy
 * nhất (giả sử)
 * 
 * - Khi tạo table trong DB để biểu thị mối quan hệ này, thì sẽ có một bảng chứa
 * khóa ngoại của bảng còn lại
 * 
 * + CascadeType.ALL: Tất cả các hoạt động (CREATE, READ, UPDATE, DELETE) trên
 * đối tượng chính cũng sẽ được áp dụng cho đối tượng liên quan
 * 
 * + CascadeType.PERSIST: Áp dụng cho hoạt động tạo mới (INSERT). Nó đảm bảo
 * rằng khi ta tạo một đối tượng gốc, đối tượng liên quan cũng sẽ được tạo nếu
 * chưa tồn tại.
 */
@Data
@Entity
@Table(name = "my_user")
public class MyUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String username;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private MyAddress address;

}