Algorithm, based on recursion that finds the shortest path from one cell to another in 2D array field.
--
Each cell contains number from -1 to 9, these numbers represent amount of blocks.
To step from one block to another, diff between them shouldn't be more than 1.
The number of blocks a player can fit in their backpack is 9
______________________________________________________________________________
Carol - Player
Commands + Rules:
<ul>
<li><code>'r'</code> Carol turns right from her own point of view.</li>
<li><code>'l'</code> Carol turns left from her own point of view.</li>
<li><code>'s'</code> Carol takes a step in the current direction of gaze. For this, the absolute height difference must be less than or equal to one.</li>
<li><code>'p'</code>  Carol places a block of ice on the field in the line of sight. To do this, she must carry at least one block of ice and the field in front of her must not have reached the maximum height (<code>9</code>). If Carol is in the water, she cannot place blocks of ice either.</li>
<li><code>'n'</code> Carol takes a block of ice from the field in the direction of view. To do this, she must be able to pick up at least one block of ice and the space in front of her must not be water (<code>-1</code>). If Carol is in the water, she cannot take any blocks of ice either.</li>
</ul>
______________________________________________________________________________
