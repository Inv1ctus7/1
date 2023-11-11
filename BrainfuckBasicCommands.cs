using System;
using System.Collections.Generic;
using System.Linq;

namespace func.brainfuck
{
	public class BrainfuckBasicCommands
	{
        public static void RegisterTo(IVirtualMachine vm, Func<int> read, Action<char> write)
        {
            vm.RegisterCommand('.', b => { write((char)b.Memory[b.MemoryPointer]); });
            vm.RegisterCommand('+', b => { b.Memory[b.MemoryPointer]++; });
            vm.RegisterCommand('-', b => { b.Memory[b.MemoryPointer]--; });
            vm.RegisterCommand(',', b => { var input = read(); if (input != -1) b.Memory[b.MemoryPointer] = (byte)input; });
			vm.RegisterCommand('>', b => { b.MemoryPointer = (b.MemoryPointer + 1) % b.Memory.Length; });
			vm.RegisterCommand('<', b => { b.MemoryPointer = (b.MemoryPointer - 1 + b.Memory.Length) % b.Memory.Length; });

            for (char c = 'A'; c <= 'Z'; c++)
            {
                byte ascii = (byte)c;
                vm.RegisterCommand(c, b => { b.Memory[b.MemoryPointer] = ascii; });
            }

            for (char c = 'a'; c <= 'z'; c++)
            {
                byte ascii = (byte)c;
                vm.RegisterCommand(c, b => { b.Memory[b.MemoryPointer] = ascii; });
            }

            for (char c = '0'; c <= '9'; c++)
            {
                byte ascii = (byte)c;
                vm.RegisterCommand(c, b => { b.Memory[b.MemoryPointer] = ascii; });
            }
        }

    }
}
