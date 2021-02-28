
package my.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
	private int teamID;
	private String teamName;
	private String teamFlag;
	private String continent;
	private int played;
	private int won;
	private int drawn;
	private int lost;
}
