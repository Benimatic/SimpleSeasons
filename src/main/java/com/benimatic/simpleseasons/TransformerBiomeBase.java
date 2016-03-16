package com.benimatic.simpleseasons;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class TransformerBiomeBase implements IClassTransformer {

	private NoiseGeneratorPerlin temperatureNoise;
	private float temperature;

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (transformedName.equals("net.minecraft.world.biome.BiomeGenBase")) {
			return transformBiomeBase(basicClass);
		} else {
			return basicClass;
		}
	}

	/**
	 * So we want to convert two lines in the getFloatTemperature method here.
	 * 
	 * We want to turn:
	 * 
	 * return this.temperature - (f + (float)p_150564_2_ - 64.0F) * 0.05F / 30.0F;
	 * and
	 * return this.temperature;
	 * 
	 * into
	 * 
	 * return CurrentSeason.getModifiedTemperature(this.temperature) - (f + (float)p_150564_2_ - 64.0F) * 0.05F / 30.0F;
	 * 
	 * This is just a matter of finding the point where java has grabbed the temperature field value and inserting
	 * a function call directly after that
	 * 
	 */
	private byte[] transformBiomeBase(byte[] bytes) {

        
        ClassNode clazz = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(clazz, 0);
        
        for (MethodNode methodNode : clazz.methods) {
        	if (methodNode.name.equals("getFloatTemperature")) {
        		// go through and find any GETFIELD net/minecraft/world/biome/BiomeGenBase.temperature instructions
        		for (int i = 0; i < methodNode.instructions.size(); i++) {
        			
        			if (methodNode.instructions.get(i).getType() == AbstractInsnNode.FIELD_INSN) {


        				FieldInsnNode fieldNode = (FieldInsnNode)methodNode.instructions.get(i);
        				
        				System.out.println("Found field instruction, " + fieldNode + " owner = " + fieldNode.owner + " name " + fieldNode.name + " desc " + fieldNode.desc); 
        				
        				
        				// don't inject unless we're getting a float, which we're assuming is the temperature
        				if (fieldNode.desc.equals("F")) {
	        				
	                        // let's get ready to INJECT!
	        				MethodInsnNode insertMethod = new MethodInsnNode(Opcodes.INVOKESTATIC, "com/benimatic/simpleseasons/CurrentSeason", "getModifiedTemperature", "(F)F", false);
	                        methodNode.instructions.insert(methodNode.instructions.get(i), insertMethod);
	                        
	                        System.out.println("Injected new instruction: " + insertMethod);
        				}

        			}
        			
        		}
        		
        	}
        }
		
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        clazz.accept(writer);
        return writer.toByteArray();
	}
	
    /**
     * Gets a floating point representation of this biome's temperature
     */
    public final float getFloatTemperature(int p_150564_1_, int p_150564_2_, int p_150564_3_)
    {
        if (p_150564_2_ > 64)
        {
            float f = (float)temperatureNoise.func_151601_a((double)p_150564_1_ * 1.0D / 8.0D, (double)p_150564_3_ * 1.0D / 8.0D) * 4.0F;
            return CurrentSeason.getModifiedTemperature(this.temperature) - (f + (float)p_150564_2_ - 64.0F) * 0.05F / 30.0F;
        }
        else
        {
            return  CurrentSeason.getModifiedTemperature(this.temperature);
        }
    }

}
