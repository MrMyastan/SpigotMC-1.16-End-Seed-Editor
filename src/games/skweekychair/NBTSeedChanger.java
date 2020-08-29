package games.skweekychair;

import java.io.IOException;
import java.util.Random;

import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.LongTag;
import net.querz.nbt.tag.StringTag;

public class NBTSeedChanger {

	public static void main(String[] args) {
		
		// Create Generic Named Tag
		NamedTag levelData = new NamedTag("Test", new StringTag("This is Test"));
		
		// create generic compound tag
		CompoundTag levelDataTag = new CompoundTag();
		
		// generate new seed
		long generatedLong = new Random().nextLong();
		
		// try catch to handle io errors
		try {
			// read in level.dat file (returns named tag with a blank name and a compound tag)
			levelData = NBTUtil.read(args[0]);
			
			// double check the tag in main tag is a compound tag (not all nbt files have a top level compound tag, and if they dont this will go very badly
			if (levelData.getTag() instanceof CompoundTag) {
				// cast the tag in named tag to a compound tag (if we are in the if block this should succeed)
				levelDataTag = (CompoundTag) levelData.getTag();
				
				// work our way down through the tree of compound tags until we got the compound tag that holds the compound tag with settings we need
				CompoundTag generatorTag = levelDataTag.getCompoundTag("Data").getCompoundTag("WorldGenSettings").getCompoundTag("dimensions").getCompoundTag("minecraft:the_end").getCompoundTag("generator");
				
				// grab the dimension seed tag and set to newly generated seed
				LongTag dimensionSeed = generatorTag.getLongTag("seed");
				dimensionSeed.setValue(generatedLong);
				
				// grab tag for biome seed (seed used when generating biomes) and set to newly generated seed
				LongTag biomeSeed = generatorTag.getCompoundTag("biome_source").getLongTag("seed");
				biomeSeed.setValue(generatedLong);
				
				// write the the edited data back into the file 
				NBTUtil.write(levelData, args[0]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// prints error (i think) if a problem goes wrong with file io (i think)
			e.printStackTrace();
		}

	}	
	
}
/*		try {
			// get NBT file data
			testRead = NBTUtil.read(args[0]);
			
			// print named tag name
			System.out.println(testRead.getName());
			
			System.out.println("-----------");
			
			// print string nbt representation of data
			System.out.println(SNBTUtil.toSNBT(testRead.getTag()));
			
			System.out.println("-----------");
			
			// check if NamedTag data is a CompoundTag
			if (testRead.getTag() instanceof CompoundTag) {
				
				System.out.println("Yes, testRead is a named CompoundTag");
				
				System.out.println("-----------");
				
				// cast Named Tag testRead to CompoundTag testCompound
				CompoundTag testCompound = (CompoundTag) testRead.getTag();
				
				// print size of compound tag
				System.out.println(testCompound.size());
				
				System.out.println("-----------");
				
				// print all of the compound tags keys
				for (String key : testCompound.keySet()) {
					System.out.println(key);
					System.out.println("-----------");
				}
				
				// is data tag (only tag in the named compound tag/top level of nbt file) a compound tag
				if (testCompound.get("Data") instanceof CompoundTag) {
					
					System.out.println("Yes, Data in testCompound is a CompoundTag");
					
					System.out.println("-----------");
					
					// cast data tag in testCompound to CompoundTag dataCompound
					CompoundTag dataCompound = (CompoundTag) testCompound.get("Data");
					
					// print size of compound tag
					System.out.println(dataCompound.size());
					
					System.out.println("-----------");
					
					// print key names in dataCompound
					for (String key : dataCompound.keySet()) {
						System.out.println(key);
						System.out.println("-----------");
						
					}
					
					// print string nbt representation of end fight
					System.out.print("DragonFight: ");
					System.out.println(SNBTUtil.toSNBT(dataCompound.get("DragonFight")));
					
					System.out.println("-----------");
					
					// is data tag (only tag in the named compound tag/top level of nbt file) a compound tag
					if (dataCompound.get("WorldGenSettings") instanceof CompoundTag) {
							
						System.out.println("Yes, world gen settings  in dataCompound is a CompoundTag");
						
						System.out.println("-----------");
							
						// cast data tag in testCompound to CompoundTag dataCompound
						CompoundTag worldGenCompound = (CompoundTag) dataCompound.get("WorldGenSettings");
						
						// print size of compound tag
						System.out.println(worldGenCompound.size());
						
						System.out.println("-----------");
						
						// print key names in dataCompound
						for (String key : worldGenCompound.keySet()) {
							System.out.println(key);
							System.out.println("-----------");
						}
						
						// print string nbt representation of seed
						System.out.print("seed: ");
						System.out.println(SNBTUtil.toSNBT(worldGenCompound.get("seed")));

						// print string nbt representation of generate_features
						System.out.print("generate_features: ");
						System.out.println(SNBTUtil.toSNBT(worldGenCompound.get("generate_features")));
						
						// is world gen compound a compound tag
						if (worldGenCompound.get("dimensions") instanceof CompoundTag) {
								
							System.out.println("Yes, dimension settings  in worldGenCompound is a CompoundTag");
							
							System.out.println("-----------");
								
							// cast data tag in testCompound to CompoundTag dataCompound
							CompoundTag dimensionsCompound = (CompoundTag) worldGenCompound.get("dimensions");
							
							// print size of compound tag
							System.out.println(dimensionsCompound.size());
							
							System.out.println("-----------");
							
							// print key names in dataCompound
							for (String key : dimensionsCompound.keySet()) {
								System.out.println(key + ":");
								CompoundTag dimCompound = (CompoundTag) dimensionsCompound.get(key);
								for (String key2 : dimCompound.keySet()) {
									if (dimCompound.get(key2) instanceof CompoundTag) {
										System.out.println("  - " + key2 + ":");
										CompoundTag dimCompound2 = (CompoundTag) dimCompound.get(key2);
										for (String key3 : dimCompound2.keySet()) {
											if (dimCompound2.get(key3) instanceof CompoundTag) {
												System.out.println("    - " + key3 + ":");
												CompoundTag dimCompound3 = (CompoundTag) dimCompound2.get(key3);
												for (String key4 : dimCompound3.keySet()) {
													System.out.print("      - " + key4 + ": ");
													System.out.println(SNBTUtil.toSNBT(dimCompound3.get(key4)));
												}
											} else {
											System.out.print("    - " + key3 + ": ");
											System.out.println(SNBTUtil.toSNBT(dimCompound2.get(key3)));
											}
										}
									} else {
									System.out.print("  - " + key2 + ": ");
									System.out.println(SNBTUtil.toSNBT(dimCompound.get(key2)));
									}
								}
								System.out.println("-----------");
								
							}
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	
	
	/*static int depth = 0;
	static String indent = "-";
	
	static void PrintCompoundTag (CompoundTag cTag) {
		for (int i = depth; i > 0; i--) {
			indent = "  " + indent;
		}
		for (String key : cTag.keySet()) {
			if (cTag.get(key) instanceof CompoundTag) {
				System.out.println(indent + key + ":");
				depth++;
				indent = "-";
				WriteCompoundTag(cTag.getCompoundTag(key));
				depth--;
				indent = "-";
				for (int i = depth; i > 0; i--) {
					indent = "  " + indent;
				}
			} else {
				try {
					System.out.println(indent + key + ": " + SNBTUtil.toSNBT(cTag.get(key)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	static void CompareCompoundTag (CompoundTag base, CompoundTag other) {
		for (int i = depth; i > 0; i--) {
			indent = "  " + indent;
		}
		for (String key : base.keySet()) {
			if (base.get(key) instanceof CompoundTag) {
				System.out.println(indent + key + ":");
				depth++;
				indent = "-";
				CompareCompoundTag(base.getCompoundTag(key), other.getCompoundTag(key));
				depth--;
				indent = "-";
				for (int i = depth; i > 0; i--) {
					indent = "  " + indent;
				}
			} else {
				System.out.println(indent + key + " is same: " + base.get(key).valueToString().equals(other.get(key).valueToString()));
			}
		}
	}*/


