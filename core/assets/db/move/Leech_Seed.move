<Move>
  <name>Leech Seed</name>
  <type>GRASS</type>
  <PP>10</PP>
  <accuracy>90</accuracy>
  <speed>0</speed>
  <DT>NONE</DT>
  <effect>
    <require>
	</require>
    <effect>
	  <SetFlag>
	    <target>target</target>
		<flag>isSeeded</flag>
		<text>$target has been seeded!</text>
	  </SetFlag>
    </effect>
  </effect>
  <isContact>false</isContact>
  <isProtectable>true</isProtectable>
  <isReflectable>true</isReflectable>
  <isSnatchable>false</isSnatchable>
  <isMirrorable>true</isMirrorable>
  <isPunch>false</isPunch>
  <isSound>false</isSound>
  <canMiss>true</canMiss>
</Move>