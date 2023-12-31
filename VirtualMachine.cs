using System;
using System.Collections.Generic;

namespace func.brainfuck
{
	public class VirtualMachine : IVirtualMachine
{
    public string Instructions { get; }
    public int InstructionPointer { get; set; }
    public byte[] Memory { get; }
    public int MemoryPointer { get; set; }
    private Dictionary<char, Action<IVirtualMachine>> commands;

    public VirtualMachine(string program, int memorySize = 30000)
    {
        Instructions = program;
        Memory = new byte[memorySize];
        commands = new Dictionary<char, Action<IVirtualMachine>>();
    }

    public void RegisterCommand(char symbol, Action<IVirtualMachine> execute)
    {
        commands[symbol] = execute;
    }

    public void Run()
    {
        while (InstructionPointer < Instructions.Length)
        {
            char instruction = Instructions[InstructionPointer];
            if (commands.ContainsKey(instruction))
            {
                commands[instruction](this);
            }
            InstructionPointer++;
        }
    }
}
}
