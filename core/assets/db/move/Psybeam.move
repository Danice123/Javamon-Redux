<Move>
  <name>Psybeam</name>
  <type>PSYCHIC</type>
  <PP>20</PP>
  <accuracy>100</accuracy>
  <speed>0</speed>
  <DT>SPECIAL</DT>
  <effect>
    <require>
	</require>
    <effect>
	  <Damage>
	    <power>65</power>
      </Damage>
	  <Chance>
	    <chance>10</chance>
		<effect>
		  <SetFlag>
			<target>target</target>
			<flag>isConfused</flag>
			<text>$target has become confused!</text>
		  </SetFlag>
		  <SetCounter>
			<target>target</target>
			<counter>ConfusionCounter</counter>
			<type>random</type>
			<max>5</max>
			<min>2</min>
		  </SetCounter>
		</effect>
	  </Chance>
    </effect>
  </effect>
  <isContact>false</isContact>
  <isProtectable>true</isProtectable>
  <isReflectable>false</isReflectable>
  <isSnatchable>false</isSnatchable>
  <isMirrorable>true</isMirrorable>
  <isPunch>false</isPunch>
  <isSound>false</isSound>
  <canMiss>true</canMiss>
</Move>